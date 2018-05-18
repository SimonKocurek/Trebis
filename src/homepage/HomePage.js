import * as React from 'react';
import { View } from 'react-native';
import AppToolbar from './Toolbar';
import AppContent from './AppContent';

export default class HomePage extends React.Component {
  render() {
    return (
        <View>
        <AppToolbar />
        <AppContent />
        </View>
    );
  }
}
