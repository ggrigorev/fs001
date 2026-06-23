/*

package:gg.vivado.boards.part0_pins

<part_info>
  <simplex>
    <part_name presence="IMPLIED" value="NMTOKEN" />
  </simplex>
  <complex>
    <pins array="pins" />
    <design_constrs repeat="?" />
  </complex>
</part_info>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.part0_pins.L_Part0Pins
    import:static gg.vivado.boards.part0_pins.U_Part0Pins
    class:PartInfo = extends Schema
    String:part_name
    ArrayList<Pins>:pins = new ArrayList<>()
    DesignConstrs:design_constrs

*/

package gg.vivado.boards.part0_pins;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.part0_pins.L_Part0Pins.*;
import static gg.vivado.boards.part0_pins.U_Part0Pins.*;

public class PartInfo extends Schema {

public String part_name = null;
public ArrayList<Pins> pins = new ArrayList<>();
public DesignConstrs design_constrs = null;

}

