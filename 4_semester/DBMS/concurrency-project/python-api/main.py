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


@app.route('/simulate-update', methods=['POST'])
def simulate_update():
    session = Session()
    band_id = request.json['id']
    try:
        # Update the user
        time.sleep(1)
        session.execute(text("UPDATE bands_concurrency SET name = 'UpdatedDuringTransaction' WHERE id = :band_id"),
                        {'band_id': band_id})
        print('python update execution')
        # result = session.execute(text("SELECT * FROM bands_concurrency WHERE id = :band_id"), {'band_id': band_id})
        # print(result.fetchone())
        time.sleep(5)  # Ensure Java has time to read this uncommitted data
        # Then roll back the change
        session.rollback()
        return jsonify({'message': 'Update applied and then rolled back'}), 200
    except Exception as e:
        session.rollback()  # Ensure rollback on error
        return jsonify({'error': str(e)}), 500
    finally:
        session.close()

if __name__ == '__main__':
    app.run(debug=True, port=8000)
