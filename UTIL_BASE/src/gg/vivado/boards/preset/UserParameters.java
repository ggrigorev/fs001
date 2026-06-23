/*

package:gg.vivado.boards.preset

<user_parameters>
  <complex>
    <user_parameter array="user_parameter" />
    <user_hier_parameter array="user_hier_parameter" />
  </complex>
</user_parameters>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.preset.L_Preset
    import:static gg.vivado.boards.preset.U_Preset
    class:UserParameters = extends Schema
    ArrayList<UserParameter>:user_parameter = new ArrayList<>()
    ArrayList<UserHierParameter>:user_hier_parameter = new ArrayList<>()

*/

package gg.vivado.boards.preset;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.preset.L_Preset.*;
import static gg.vivado.boards.preset.U_Preset.*;

public class UserParameters extends Schema {

public ArrayList<UserParameter> user_parameter = new ArrayList<>();
public ArrayList<UserHierParameter> user_hier_parameter = new ArrayList<>();

}

