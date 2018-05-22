import * as React from 'react';
import { View, ScrollView } from 'react-native';
import { Checkbox } from 'react-native-paper';
import AppToolbar from '../components/Toolbar';

export default class Settings extends React.Component {
  state = {
    checked: false,
  };

  render() {
    const { checked } = this.state;

    return (
      <View>
        <AppToolbar navigation={this.props.navigation} />
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
