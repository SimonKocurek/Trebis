import * as React from 'react';
import { Toolbar, ToolbarBackAction, ToolbarContent, ToolbarAction, Paper, Text, TouchableRipple } from 'react-native-paper';
import { View, StatusBar, Navigator } from 'react-native';
import { theme } from '../App';

export default class AppToolbar extends React.Component {

  constructor(props) {
    super(props);

    this.state = {
      kebabOpened: false
    };
  }

  switchKebabMenu() {
    this.setState((previous, props) => {
      return { kebabOpened: !previous.kebabOpened };
    });
  }

  render() {
    return (
      <View>
        <StatusBar backgroundColor={theme.colors.primaryDark} />
        <Toolbar>
          {this.props.initial || <ToolbarBackAction onPress={() => this.props.navigation.goBack()} />}
          <ToolbarContent title={this.props.title} />
          <ToolbarAction icon="more-vert" onPress={() => this.switchKebabMenu()} />
        </Toolbar>
        {this.state.kebabOpened && (
          <Paper
            style={{
              position: 'absolute',
              right: 6,
              top: 60,
              padding: 20,
              alignItems: 'center',
              justifyContent: 'center',
              elevation: 5,
            }}>
            <TouchableRipple
              onPress={() => this.props.navigation.navigate('Settings')}
              rippleColor={theme.colors.primary}
            >
              <Text>Settings</Text>
            </TouchableRipple>
          </Paper>
        )}
      </View>
    );
  }
}