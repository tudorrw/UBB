import React, { useState } from 'react';
import Button from '@mui/material/Button';
import ButtonGroup from '@mui/material/ButtonGroup';
import Paper from '@mui/material/Paper';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import axios from 'axios';

function App() {
  const [bandId, setBandId] = useState('');
  const [selectedIssue, setSelectedIssue] = useState(null);
  const [issueResponse, setIssueResponse] = useState(null);
  const [loading, setLoading] = useState(false); // New state to track loading state

  const sendRequest = async (endpoint) => {
    try {
      if (!bandId) {
        alert("Please enter a Band ID.");
        return;
      }
      setLoading(true); // Set loading state to true when request is sent
      const response = await axios.post(`http://localhost:8080/java-concurrency/${endpoint}?bandId=${bandId}`, { 
        bandId : bandId,
      });
      if(response.status === 200) {
        setIssueResponse(response.data); // Set response
      }
      setLoading(false); // Set loading state to false after response is received
    } catch (error) {
      console.error('Error:', error);
      alert("Error occurred requesting to the backend.");
      setLoading(false); // Set loading state to false if there is an error
    }
    
  };
  const getIssueDescription = (issue) => {
    switch(issue) {
      case 'dirty-write':
        return "Dirty Write: This occurs when two transactions write to the same data concurrently, and one overwrites the changes made by the other without first checking for conflicts.";
      case 'lost-update':
        return "Lost Update: This occurs when one transaction overwrites changes made by another transaction before the latter transaction is committed.";
      case 'unrepetable-reads':
        return "Unrepeatable Reads: This occurs when a transaction reads data multiple times and gets different results each time because another transaction has modified the data in between.";
      case 'dirty-read':
        return "Dirty Read: This occurs when one transaction reads data that has been modified by another transaction but not yet committed.";
      case 'phantom-read':
        return "Phantom Read: This occurs when a transaction reads a set of rows that satisfy a certain condition, but another transaction inserts or deletes rows that also satisfy the same condition, causing the first transaction to see different results.";
      default:
        return "";
    }
  };

  return (
    <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh' }}>
      <Paper elevation={3} sx={{ p: 4}}>
        <Typography variant="h5" gutterBottom>
          Concurrency Issues
        </Typography>
        <div className='input-container' style={{ marginBottom: '16px' }}>
          <input type="text" placeholder="Enter Band ID (not for Phantom Read)" value={bandId} onChange={(e) => setBandId(e.target.value)} style={{ width: '30%', padding: '8px', fontSize: '16px' }} />
        </div>
        <ButtonGroup variant="contained" color="primary" aria-label="contained primary button group">
          <Button onClick={() => { setSelectedIssue(selectedIssue === 'dirty-write' ? null : 'dirty-write'); selectedIssue !== 'dirty-write' && sendRequest('dirty-write'); }}>{selectedIssue === 'dirty-write' ? 'Hide Dirty Write' : 'Dirty Write'}</Button>
          <Button onClick={() => { setSelectedIssue(selectedIssue === 'lost-update' ? null : 'lost-update'); selectedIssue !== 'lost-update' && sendRequest('lost-update'); }}>{selectedIssue === 'lost-update' ? 'Hide Lost Update' : 'Lost Update'}</Button>
          <Button onClick={() => { setSelectedIssue(selectedIssue === 'unrepetable-reads' ? null : 'unrepetable-reads'); selectedIssue !== 'unrepetable-reads' && sendRequest('unrepetable-reads'); }}>{selectedIssue === 'unrepetable-reads' ? 'Hide Unrepeatable Reads' : 'Unrepeatable Reads'}</Button>
          <Button onClick={() => { setSelectedIssue(selectedIssue === 'dirty-read' ? null : 'dirty-read'); selectedIssue !== 'dirty-read' && sendRequest('dirty-read'); }}>{selectedIssue === 'dirty-read' ? 'Hide Dirty Read' : 'Dirty Read'}</Button>
          <Button onClick={() => { setSelectedIssue(selectedIssue === 'phantom-read' ? null : 'phantom-read'); selectedIssue !== 'phantom-read' && sendRequest('phantom-read'); }}>{selectedIssue === 'phantom-read' ? 'Hide Phantom Read' : 'Phantom Read'}</Button>

        </ButtonGroup>
        {selectedIssue && (
        <Box sx={{ mt: 2 }}>
          <Typography variant="body1" gutterBottom>
            {getIssueDescription(selectedIssue)}
          </Typography>
          {loading ? ( // Check if loading state is true
            <Typography variant="body2" gutterBottom>
              Loading...
            </Typography>
          ) : (
            <>
              <Typography variant="body2" gutterBottom>
                Response: 
              </Typography>
              <pre>{JSON.stringify(issueResponse, null, 2)}</pre>
            </>
          )}
        </Box>
      )}
      </Paper>
    </Box>
  );
}

export default App;
