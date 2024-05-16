import time
from flask import Flask, request, jsonify
from sqlalchemy import create_engine, text
from sqlalchemy.orm import sessionmaker

user = 'dev'
password = '1234'
host = 'localhost'
port = 3306
database = 'music-festivals'
 
# PYTHON FUNCTION TO CONNECT TO THE MYSQL DATABASE AND
# RETURN THE SQLACHEMY ENGINE OBJECT
def get_connection():
    return create_engine(
        url="mysql+pymysql://{0}:{1}@{2}:{3}/{4}".format(
            user, password, host, port, database
        )
    )
engine = get_connection()   
Session = sessionmaker(bind=engine) 
app = Flask(__name__)


@app.route('/dirty-read-python', methods=['POST'])
def dirtyRead():
    session = Session()
    band_id = request.json['id']
    try:
        # Update the user
        time.sleep(1)
        session.execute(text("UPDATE bands_concurrency SET name = 'UpdatedPython' WHERE id = :band_id"),
                        {'band_id': band_id})
        print('python update execution')
        result = session.execute(text("SELECT * FROM bands_concurrency WHERE id = :band_id"), {'band_id': band_id})
        print(result.fetchone())
        time.sleep(5)  # Ensure Java has time to read this uncommitted data
        # Then roll back the change
        session.rollback()
        return jsonify({'message': 'Update applied and then rolled back'}), 200
    except Exception as e:
        session.rollback()  # Ensure rollback on error
        return jsonify({'error': str(e)}), 500
    finally:
        session.close()

@app.route('/lost-update-python', methods=['POST'])
def lostUpdate():
    session = Session()
    band_id = request.json['id']
    try:
        time.sleep(1)
        result = session.execute(text("SELECT * FROM bands_concurrency WHERE id = :band_id FOR UPDATE"),
                                 {'band_id': band_id})
        print("Before: ", result.fetchone())
        session.execute(text("UPDATE bands_concurrency SET name = 'UpdatedPython' WHERE id = :band_id"),
            {'band_id': band_id})
        # Simulate a delay before applying the update
        result = session.execute(text("SELECT * FROM bands_concurrency WHERE id = :band_id FOR UPDATE"),
                            {'band_id': band_id})
        print("During: ", result.fetchone())
        session.commit()
        return jsonify({'message': 'Update applied and then rolled back'}), 200
    except Exception as e:
        session.rollback()  # Ensure rollback on error
        return jsonify({'error': str(e)}), 500
    finally:
        session.close()


@app.route('/unrepetable-reads-python', methods=['POST'])
def unrepetableRead():
    session = Session()
    band_id = request.json['id']
    
    try:
        time.sleep(1)  # Ensure Java has time to read before updating
        session.execute(text("UPDATE bands_concurrency SET name = 'UpdatedPython' WHERE id = :band_id"),
            {'band_id': band_id})
        result = session.execute(text("SELECT * FROM bands_concurrency WHERE id = :band_id FOR UPDATE"),
                            {'band_id': band_id})
        print("During: ", result.fetchone())
        session.commit()
        return jsonify({'message': 'Update applied'}), 200
    except Exception as e:
        session.rollback()  # Ensure rollback on error
        return jsonify({'error': str(e)}), 500
    finally:
        session.close()




@app.route('/dirty-write-python', methods=['POST'])
def dirtyWrite():
    session = Session()
    band_id = request.json['id']
    try:
        time.sleep(1)
        session.execute(text("UPDATE bands_concurrency SET name = 'DirtyWritePython' WHERE id = :band_id"),
                        {'band_id': band_id})
        print("Python update executed")
        result = session.execute(text("SELECT * FROM bands_concurrency WHERE id = :band_id"), {'band_id': band_id})
        print(result.fetchone())
        session.commit()
        
        return jsonify({'message': 'Update applied and committed'}), 200
    except Exception as e:
        session.rollback()
        return jsonify({'error': str(e)}), 500
    finally:
        session.close()



@app.route('/phantom-read-python', methods=['POST'])
def simulate_insert():
    session = Session()
    try:
        time.sleep(1)  # Wait to ensure the Java transaction is in progress
        session.execute(text("INSERT INTO bands_concurrency (name, country, forming_year) VALUES ('NewBand', 'CountryX', 2024)"))
        print("Python insert executed")
        session.commit()
        return jsonify({'message': 'Insert applied and committed'}), 200
    except Exception as e:
        session.rollback()
        return jsonify({'error': str(e)}), 500
    finally:
        session.close()

if __name__ == '__main__':
    app.run(debug=True, port=8000)
