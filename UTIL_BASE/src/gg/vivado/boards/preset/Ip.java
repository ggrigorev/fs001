/*

package:gg.vivado.boards.preset

<ip>
  <simplex>
    <vendor presence="REQUIRED" value="CDATA" />
    <library presence="REQUIRED" value="CDATA" />
    <name presence="REQUIRED" value="CDATA" />
    <version presence="IMPLIED" value="CDATA" />
    <ip_interface presence="IMPLIED" value="CDATA" />
  </simplex>
  <complex>
    <user_parameters />
    <model_parameters />
  </complex>
</ip>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.preset.L_Preset
    import:static gg.vivado.boards.preset.U_Preset
    class:Ip = extends Schema
    String:vendor
    String:library
    String:name
    String:version
    String:ip_interface
    UserParameters:user_parameters
    ModelParameters:model_parameters

*/

package gg.vivado.boards.preset;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.preset.L_Preset.*;
import static gg.vivado.boards.preset.U_Preset.*;

public class Ip extends Schema {

public String vendor = null;
public String library = null;
public String name = null;
public String version = null;
public String ip_interface = null;
public UserParameters user_parameters = null;
public ModelParameters model_parameters = null;

}

