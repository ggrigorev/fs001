/*

package:gg.vivado.boards.part0_pins

<internal_vref>
  <simplex>
    <bank_id presence="REQUIRED" value="CDATA" />
    <voltage presence="REQUIRED" value="CDATA" />
  </simplex>
</internal_vref>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.part0_pins.L_Part0Pins
    import:static gg.vivado.boards.part0_pins.U_Part0Pins
    class:InternalVref = extends Schema
    String:bank_id
    String:voltage

*/

package gg.vivado.boards.part0_pins;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.part0_pins.L_Part0Pins.*;
import static gg.vivado.boards.part0_pins.U_Part0Pins.*;

public class InternalVref extends Schema {

public String bank_id = null;
public String voltage = null;

}

