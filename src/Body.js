import * as React from 'react';
import { View } from 'react-native';
import { Button } from 'react-native-paper';
import AppToolbar from './Toolbar';
import AppContent from './AppContent';

export default class Body extends React.Component {

  render() {
    return (
      <View>
        <AppToolbar />
        <AppContent />
      </View>
    );
  }
}