import * as React from 'react';
import { View, ScrollView } from 'react-native';
import { Checkbox } from 'react-native-paper';

export class SettingsPage extends React.Component {

  static navigationOptions = {
    title: 'Settings',
  };

  state = {
    checked: false,
  };

  render() {
    const { checked } = this.state;

    return (
      <View>
        <ScrollView>
          <Checkbox
            checked={checked}
            onPress={() => { this.setState({ checked: !checked }); }}
          />
          <Checkbox
            checked={checked}
            onPress={() => { this.setState({ checked: !checked }); }}
          />
          <Checkbox
            checked={checked}
            onPress={() => { this.setState({ checked: !checked }); }}
          />
          <Checkbox
            checked={checked}
            onPress={() => { this.setState({ checked: !checked }); }}
          />
          <Checkbox
            checked={checked}
            onPress={() => { this.setState({ checked: !checked }); }}
          />
          <Checkbox
            checked={checked}
            onPress={() => { this.setState({ checked: !checked }); }}
          />
          <Checkbox
            checked={checked}
            onPress={() => { this.setState({ checked: !checked }); }}
          />
        </ScrollView>
      </View>
    );
  }
}
