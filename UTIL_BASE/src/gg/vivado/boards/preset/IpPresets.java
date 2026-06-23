/*

package:gg.vivado.boards.preset

<ip_presets>
  <simplex>
    <schema presence="REQUIRED" value="CDATA" />
  </simplex>
  <complex>
    <ip_preset array="ip_preset" />
  </complex>
</ip_presets>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.preset.L_Preset
    import:static gg.vivado.boards.preset.U_Preset
    class:IpPresets = extends Schema
    String:schema
    ArrayList<IpPreset>:ip_preset = new ArrayList<>()

*/

package gg.vivado.boards.preset;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.preset.L_Preset.*;
import static gg.vivado.boards.preset.U_Preset.*;

public class IpPresets extends Schema {

public String schema = null;
public ArrayList<IpPreset> ip_preset = new ArrayList<>();

}

