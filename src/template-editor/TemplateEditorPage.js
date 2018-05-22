import * as React from 'react';
import { View, ScrollView } from 'react-native';
import { Searchbar } from 'react-native-paper';

export default class TemplateEditor extends React.Component {
  
  static navigationOptions = {
    title: 'Settings',
  };
  
  constructor(props) {
    super(props);
    this.state = {
      firstQuery: ''
    };
  }

  render() {
    return (
      <View>
        <ScrollView>
          <Searchbar
            placeholder="Search"
            onChangeText={query => { this.setState({ firstQuery: query }); }}
            value={this.state.firstQuery}
          />
        </ScrollView>
      </View>
    );
  }
}
