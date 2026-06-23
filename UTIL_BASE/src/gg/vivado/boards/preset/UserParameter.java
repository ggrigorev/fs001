/*

package:gg.vivado.boards.preset

<user_parameter>
  <simplex>
    <name presence="REQUIRED" value="CDATA" />
    <value presence="REQUIRED" value="CDATA" />
    <value_type presence="IMPLIED" value="CDATA" />
  </simplex>
</user_parameter>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.preset.L_Preset
    import:static gg.vivado.boards.preset.U_Preset
    class:UserParameter = extends Schema
    String:name
    String:value
    String:value_type

*/

package gg.vivado.boards.preset;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.preset.L_Preset.*;
import static gg.vivado.boards.preset.U_Preset.*;

public class UserParameter extends Schema {

public String name = null;
public String value = null;
public String value_type = null;

}

