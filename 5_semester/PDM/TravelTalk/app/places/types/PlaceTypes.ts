export interface Place {
  id: string;
  name: string;
  image: string;
  distance: number; // Changed from string to number
  coordinate: {
    latitude: number;
    longitude: number;
  };
  vicinity?: string;
}

export interface LocationState {
  latitude: number;
  longitude: number;
  latitudeDelta: number;
  longitudeDelta: number;
}
