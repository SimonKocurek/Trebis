import * as React from 'react';
import { View } from 'react-native';
import { AppContent } from './AppContent';
import { ToolbarAction } from 'react-native-paper';
import { Store } from '../Store';

export class HomePage extends React.Component {

  static navigationOptions = {
    title: 'Trebis',
    headerRight: <ToolbarAction icon="more-vert" dark="false" onPress={() => Store.kebabOpened = !Store.kebabOpened} />
  };

  render() {
    return (
      <View style={{ flex: 1 }}>
        <AppContent navigation={this.props.navigation} />
      </View>
    );
  }
}
