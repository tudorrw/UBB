import { Button, StyleSheet } from 'react-native';
import { Text, View } from 'react-native';
import { FIREBASE_AUTH } from '@/FirebaseConfig';
import { getAuth } from 'firebase/auth';
import { router } from 'expo-router'; // Ensure router is imported
import React from 'react';

export default function Layout() {
  const [isLoading, setIsLoading] = React.useState(true);

  // Monitor authentication state
  React.useEffect(() => {
    const unsubscribe = getAuth().onAuthStateChanged((user) => {
      setIsLoading(false);
      if (user) {
        console.log("User is logged in, redirecting to /maps...");
        router.replace("/maps"); // Redirect to maps
      
      } else {
        console.log("No user found, redirecting to /...");
        router.replace("/"); // Redirect to home if not logged in
      }
    });
    return unsubscribe;
  }, []);

  if (isLoading) return <Text style={{ paddingTop: 30 }}>Loading...</Text>;

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Tab Two</Text>
      <View style={styles.separator}/>
      <Button title="Sign Out" onPress={() => FIREBASE_AUTH.signOut()} />
      <Button title="Delete Account" onPress={() => FIREBASE_AUTH.currentUser?.delete()} />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  title: {
    fontSize: 20,
    fontWeight: 'bold',
  },
  separator: {
    marginVertical: 30,
    height: 1,
    width: '80%',
  },
});
