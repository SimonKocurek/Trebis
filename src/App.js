import * as React from 'react';
import { YellowBox } from 'react-native';
import { DefaultTheme, Provider as PaperProvider } from 'react-native-paper';
import { createStackNavigator } from 'react-navigation';
import HomePage from './homepage/HomePage';

const theme = {
  ...DefaultTheme,
  roundness: 7,
  colors: {
    ...DefaultTheme.colors,
    primary: '#03A9F4',
    accent: '#c51162'
  },
};

export default class App extends React.Component {
  render() {
    return (
      <PaperProvider theme={theme}>
        <RootStack />
      </PaperProvider>
    );
  }
}

const RootStack = createStackNavigator({
  Home: {
    screen: HomePage
  },
});

YellowBox.ignoreWarnings(['Warning: isMounted(...) is deprecated']);
