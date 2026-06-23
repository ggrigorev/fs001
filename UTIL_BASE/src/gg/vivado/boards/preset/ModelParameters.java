/*

package:gg.vivado.boards.preset

<model_parameters>
  <complex>
    <model_parameter array="model_parameter" />
  </complex>
</model_parameters>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.preset.L_Preset
    import:static gg.vivado.boards.preset.U_Preset
    class:ModelParameters = extends Schema
    ArrayList<ModelParameter>:model_parameter = new ArrayList<>()

*/

package gg.vivado.boards.preset;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.preset.L_Preset.*;
import static gg.vivado.boards.preset.U_Preset.*;

public class ModelParameters extends Schema {

public ArrayList<ModelParameter> model_parameter = new ArrayList<>();

}

