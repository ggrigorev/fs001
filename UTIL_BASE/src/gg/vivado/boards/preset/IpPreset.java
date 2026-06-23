/*

package:gg.vivado.boards.preset

<ip_preset>
  <simplex>
    <preset_proc_name presence="REQUIRED" value="CDATA" />
  </simplex>
  <complex>
    <ip array="ip" />
  </complex>
</ip_preset>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.preset.L_Preset
    import:static gg.vivado.boards.preset.U_Preset
    class:IpPreset = extends Schema
    String:preset_proc_name
    ArrayList<Ip>:ip = new ArrayList<>()

*/

package gg.vivado.boards.preset;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.preset.L_Preset.*;
import static gg.vivado.boards.preset.U_Preset.*;

public class IpPreset extends Schema {

public String preset_proc_name = null;
public ArrayList<Ip> ip = new ArrayList<>();

}

