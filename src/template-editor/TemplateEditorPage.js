import * as React from 'react';
import { View, ScrollView } from 'react-native';
import { Searchbar } from 'react-native-paper';
import AppToolbar from '../components/Toolbar';

export default class TemplateEditor extends React.Component {
  render() {
    return (
      <View>
        <AppToolbar />
        <ScrollView>
          <Searchbar
            placeholder="Search"
            onChangeText={query => { this.setState({ firstQuery: query }); }}
            value={firstQuery}
          />
        </ScrollView>
      </View>
    );
  }
}
