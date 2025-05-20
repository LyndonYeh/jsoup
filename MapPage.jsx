//npm install leaflet react-leaflet@4.2.1
import React from 'react';
import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet';
import 'leaflet/dist/leaflet.css';
import './MapPage.css'

// 手動指定圖標路徑（解決標記不顯示問題）
import markerIcon2x from 'leaflet/dist/images/marker-icon-2x.png';
import markerIcon from 'leaflet/dist/images/marker-icon.png';
import markerShadow from 'leaflet/dist/images/marker-shadow.png';

import L from 'leaflet';


L.Icon.Default.mergeOptions({
  iconRetinaUrl: markerIcon2x,
  iconUrl: markerIcon,
  shadowUrl: markerShadow,
});

const customIcon = new L.Icon({
  iconUrl: '/images/vite.svg',
  iconSize: [32, 32],
  iconAnchor: [16, 32],
  popupAnchor: [0, -32],
});

const staticSpots = [
  {
    id: 1,
    name: "台北市信義區 市府轉運站旁停車格",
    lat: 25.0408,
    lng: 121.5645,
    type: "綠P"
  },
  {
    id: 2,
    name: "台北市中山區 圓山花博停車場",
    lat: 25.0701,
    lng: 121.5191,
    type: "黃P"
  },
  {
    id: 3,
    name: "台北市大安區 建國南路高架橋下",
    lat: 25.0336,
    lng: 121.5432,
    type: "綠P"
  }
];


const MapPage = () => {
  return (
    <div style={{ position: 'relative' }}>
    <MapContainer
      center={[25.0478, 121.5319]} // 台北
      zoom={13}
      scrollWheelZoom={true}
      style={{ height: '100vh', width: '100%' }}
    >
      <TileLayer
        attribution='© OpenStreetMap'
        url='https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png'
      />
      <Marker position={[25.0478, 121.5319]}>
        <Popup>台北市中心</Popup>
      </Marker>

      {staticSpots.map(spot => (
      <Marker key={spot.id} position={[spot.lat, spot.lng]} icon={customIcon}>
        <Popup>
          {spot.name}<br />類型：{spot.type}
        </Popup>
      </Marker>
      ))}
    </MapContainer>
    <div className="map-legend" >
      <h4>圖例</h4>
        <div><span className="legend-color green"></span> 綠P</div>
        <div><span className="legend-color yellow"></span> 黃P</div>
    </div>

    </div>
  );
};

export default MapPage;
