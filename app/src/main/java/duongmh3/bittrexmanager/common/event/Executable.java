package duongmh3.bittrexmanager.common.event;

import java.io.Serializable;

/**
 * Created by doanthanhduong on 12/12/16.
 */
@FunctionalInterface
public interface Executable extends Serializable {
    void execute();
}
