import * as React from 'react';
import { Toolbar, ToolbarBackAction, ToolbarContent, ToolbarAction } from 'react-native-paper';

export default class AppToolbar extends React.Component {
  render() {
    return (
      <Toolbar>
        <ToolbarContent
          title={'Trebis'}
        />
      </Toolbar>
    );
  }
}