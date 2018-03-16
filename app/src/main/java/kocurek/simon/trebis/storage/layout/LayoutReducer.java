package kocurek.simon.trebis.storage.layout;

import com.yheriatovych.reductor.Reducer;
import com.yheriatovych.reductor.annotations.AutoReducer;

import java.util.ArrayList;
import java.util.List;

@AutoReducer
public abstract class LayoutReducer implements Reducer<List<Layout>> {

    @AutoReducer.InitialState
    List initialState() {
        return new ArrayList();
    }

    @AutoReducer.Action(LayoutActions.GET)
    List get(List<Layout> state, int id) {

    }

    @AutoReducer.Action(LayoutActions.GET_ALL)
    List getAll(List<Layout> state) {

    }

    @AutoReducer.Action(LayoutActions.GET_HISTORY)
    List getHistory(List<Layout> state, int id) {

    }

    @AutoReducer.Action(LayoutActions.ADD)
    List add(List<Layout> state, Layout layout) {

    }

    @AutoReducer.Action(LayoutActions.UPDATE)
    List update(List<Layout> state, int id, Layout layout) {

    }

    @AutoReducer.Action(LayoutActions.DELETE)
    List delete(List<Layout> state, int id) {

    }

    public static LayoutReducer create() {
        return new LayoutReducerImpl();
    }

}
