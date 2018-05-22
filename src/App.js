import * as React from 'react';
import { YellowBox } from 'react-native';
import { DefaultTheme, Provider as PaperProvider } from 'react-native-paper';
import { createStackNavigator } from 'react-navigation';
import HomePage from './homepage/HomePage';
import SettingsPage from './settings/SettingsPage';
import TemplateEditor from './template-editor/TemplateEditorPage';

export const theme = {
  ...DefaultTheme,
  roundness: 7,
  colors: {
    ...DefaultTheme.colors,
    primary: '#AB47BC',
    primaryDark: '#7B1FA2',
    accent: '#9C27B0'
  },
};

const RootStack = createStackNavigator(
  {
    Home: { screen: HomePage },
    Settings: { screen: SettingsPage },
    TemplateEditor: { screen: TemplateEditor },
  },
  {
    initialRouteName: 'Home',
    headerMode: 'none'
  }
);

export default class App extends React.Component {
  render() {
    return (
      <PaperProvider style={{ flex: 1 }} theme={theme}>
        <RootStack />
      </PaperProvider>
    );
  }
}

YellowBox.ignoreWarnings(['Warning: isMounted(...) is deprecated']);
