import * as React from 'react';
import { YellowBox } from 'react-native';
import { DefaultTheme, Provider as PaperProvider } from 'react-native-paper';
import { createStackNavigator } from 'react-navigation';
import HomePage from './homepage/HomePage';
import SettingsPage from './settings/SettingsPage';
import TemplateEditor from './template-editor/TemplateEditorPage';

const theme = {
  ...DefaultTheme,
  roundness: 7,
  colors: {
    ...DefaultTheme.colors,
    primary: '#03A9F4',
    accent: '#c51162'
  },
};

const RootStack = createStackNavigator(
  {
    Home: { screen: HomePage},
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
      <PaperProvider theme={theme}>
        <RootStack />
      </PaperProvider>
    );
  }
}

YellowBox.ignoreWarnings(['Warning: isMounted(...) is deprecated']);
