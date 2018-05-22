import * as React from 'react';
import { View } from 'react-native';
import AppToolbar from '../components/Toolbar';
import AppContent from './AppContent';

export default class HomePage extends React.Component {
  render() {
    return (
        <View>
        <AppToolbar navigation={this.props.navigation} />
        <AppContent />
        </View>
    );
  }
}
