import * as React from 'react';
import { DefaultTheme, Provider as PaperProvider } from 'react-native-paper';
import Body from './src/Body';

const theme = {
  ...DefaultTheme,
  roundness: 7,
  colors: {
    ...DefaultTheme.colors,
    primary: '#03A9F4',
    accent: '#8BC34A'
  },
};

export default class App extends React.Component {
  render() {
    return (
      <PaperProvider theme={theme}>
        <Body/>
      </PaperProvider>
    );
  }
}
