import * as React from 'react';
import { View } from 'react-native';
import { FAB } from 'react-native-paper';

export default class AppContent extends React.Component {

    render() {
        return (
            <View style={{
                flexDirection: 'row',
                height: 100,
                padding: 20,
              }}>
                <View style={{ flex: 1, flexDirection: 'row' }}>
                    <View style={{ width: 50, height: 50, backgroundColor: 'powderblue' }} />
                    <View style={{ width: 50, height: 50, backgroundColor: 'skyblue' }} />
                    <View style={{ width: 50, height: 50, backgroundColor: 'steelblue' }} />
                </View>

                <View style={{ width: 50 }}>
                    <FAB
                        small
                        icon="add"
                        onPress={() => { }} />
                </View>
            </View>
        );
    }
}