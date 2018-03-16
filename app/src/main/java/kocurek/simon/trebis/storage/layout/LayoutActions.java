package kocurek.simon.trebis.storage.layout;

import com.yheriatovych.reductor.Action;
import com.yheriatovych.reductor.annotations.ActionCreator;

@ActionCreator
public interface LayoutActions {

    String GET = "[Layout] :: Get";
    String GET_ALL = "[Layout] :: Get All";
    String GET_HISTORY = "[Layout] :: Get History";
    String ADD = "[Layout] :: Add";
    String UPDATE = "[Layout] :: Update";
    String DELETE = "[Layout] :: Delete";

    @ActionCreator.Action(LayoutActions.GET)
    Action get(int id);

    @ActionCreator.Action(LayoutActions.GET_ALL)
    Action getAll();

    @ActionCreator.Action(LayoutActions.GET_HISTORY)
    Action getHistory(int id);

    @ActionCreator.Action(LayoutActions.ADD)
    Action add(Layout layout);

    @ActionCreator.Action(LayoutActions.UPDATE)
    Action update(int id, Layout layout);

    @ActionCreator.Action(LayoutActions.DELETE)
    Action delete(int id);

}
