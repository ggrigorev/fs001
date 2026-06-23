/*

package:gg.vivado.boards.preset

<user_hier_parameter>
  <simplex>
    <name presence="REQUIRED" value="CDATA" />
  </simplex>
  <complex>
    <user_hier_parameter array="user_hier_parameter" />
    <user_parameter array="user_parameter" />
  </complex>
</user_hier_parameter>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.preset.L_Preset
    import:static gg.vivado.boards.preset.U_Preset
    class:UserHierParameter = extends Schema
    String:name
    ArrayList<UserHierParameter>:user_hier_parameter = new ArrayList<>()
    ArrayList<UserParameter>:user_parameter = new ArrayList<>()

*/

package gg.vivado.boards.preset;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.preset.L_Preset.*;
import static gg.vivado.boards.preset.U_Preset.*;

public class UserHierParameter extends Schema {

public String name = null;
public ArrayList<UserHierParameter> user_hier_parameter = new ArrayList<>();
public ArrayList<UserParameter> user_parameter = new ArrayList<>();

}

