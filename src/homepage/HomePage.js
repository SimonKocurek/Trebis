import * as React from 'react';
import { View } from 'react-native';
import AppContent from './AppContent';

export default class HomePage extends React.Component {

  static navigationOptions = {
    title: 'Trebis',
  };

  render() {
    return (
      <View style={{ flex: 1 }}>
        <AppContent navigation={this.props.navigation} />
      </View>
    );
  }
}
