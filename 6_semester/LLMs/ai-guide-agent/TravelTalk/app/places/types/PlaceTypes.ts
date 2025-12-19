export interface Place {
  id: string;
  name: string;
  image: string;
  distance: number;
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
