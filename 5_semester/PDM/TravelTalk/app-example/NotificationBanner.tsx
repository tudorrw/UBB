// NotificationBanner.tsx
import React, { useEffect } from 'react';
import { StyleSheet, View, Text, Button } from 'react-native';

interface Notification {
  message: string;
  actionText?: string; // Optional action button text
  onAction?: () => void; // Optional callback for action
}

interface NotificationBannerProps {
  notifications: Notification[];
  onDismiss: (index: number) => void; // Callback to dismiss specific notification
}

const NotificationBanner: React.FC<NotificationBannerProps> = ({ notifications, onDismiss }) => {
  useEffect(() => {
    notifications.forEach((_, index) => {
      const timeout = setTimeout(() => {
        onDismiss(index);
      }, 4000); // Auto dismiss after 4 seconds
      return () => clearTimeout(timeout);
    });
  }, [notifications]);

  return (
    <View style={styles.container}>
      {notifications.map((notification, index) => (
        <View key={index} style={styles.messageContainer}>
          <Text style={styles.message}>{notification.message}</Text>
          {notification.actionText && (
            <Button
              title={notification.actionText}
              onPress={() => {
                if (notification.onAction) {
                  notification.onAction(); // Execute the action if provided
                }
                onDismiss(index); // Dismiss the notification after action
              }}
            />
          )}
        </View>
      ))}
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    position: 'absolute',
    top: 0,
    left: 0,
    right: 0,
    zIndex: 1000,
    padding: 10,
    backgroundColor: '#ffcc00', // Customize your background color
  },
  messageContainer: {
    marginBottom: 5,
    padding: 10,
    backgroundColor: '#fff', // Customize your message background
    borderRadius: 5,
  },
  message: {
    fontSize: 16,
  },
});

export default NotificationBanner;
