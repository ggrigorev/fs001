/*

package:gg.vivado.boards.part0_pins

<dci_cascade>
  <simplex>
    <master_bank_id presence="REQUIRED" value="CDATA" />
    <slave_bank_ids presence="REQUIRED" value="CDATA" />
  </simplex>
</dci_cascade>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.part0_pins.L_Part0Pins
    import:static gg.vivado.boards.part0_pins.U_Part0Pins
    class:DciCascade = extends Schema
    String:master_bank_id
    String:slave_bank_ids

*/

package gg.vivado.boards.part0_pins;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.part0_pins.L_Part0Pins.*;
import static gg.vivado.boards.part0_pins.U_Part0Pins.*;

public class DciCascade extends Schema {

public String master_bank_id = null;
public String slave_bank_ids = null;

}

