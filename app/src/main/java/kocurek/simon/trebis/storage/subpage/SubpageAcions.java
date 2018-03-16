package kocurek.simon.trebis.storage.subpage;

import com.yheriatovych.reductor.Action;
import com.yheriatovych.reductor.annotations.ActionCreator;

@ActionCreator
public interface SubpageAcions {

    String GET = "[Subpage] :: Get";
    String ADD = "[Subpage] :: Add";
    String UPDATE = "[Subpage] :: Update";
    String DELETE = "[Subpage] :: Delete";

    @ActionCreator.Action(SubpageAcions.GET)
    Action get(int layoutId);

    @ActionCreator.Action(SubpageAcions.ADD)
    Action add(int layoutId, Subpage subpage);

    @ActionCreator.Action(SubpageAcions.UPDATE)
    Action update(int subpageId, Subpage subpage);

    @ActionCreator.Action(SubpageAcions.DELETE)
    Action delete(int subpageId);

}
