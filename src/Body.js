import * as React from 'react';
import { View } from 'react-native';
import { Button } from 'react-native-paper';
import AppToolbar from './Toolbar';

export default class Body extends React.Component {

  render() {
    return (
      <View>
        <AppToolbar/>
        <Button primary raised onPress={() => console.log('Pressed')}>
          Press me
        </Button>
      </View>
    );
  }
}