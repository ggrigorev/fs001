/*

package:gg.vivado.boards.part0_pins

<design_constrs>
  <complex>
    <dci_cascade array="dci_cascade" />
    <internal_vref array="internal_vref" />
  </complex>
</design_constrs>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.part0_pins.L_Part0Pins
    import:static gg.vivado.boards.part0_pins.U_Part0Pins
    class:DesignConstrs = extends Schema
    ArrayList<DciCascade>:dci_cascade = new ArrayList<>()
    ArrayList<InternalVref>:internal_vref = new ArrayList<>()

*/

package gg.vivado.boards.part0_pins;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.part0_pins.L_Part0Pins.*;
import static gg.vivado.boards.part0_pins.U_Part0Pins.*;

public class DesignConstrs extends Schema {

public ArrayList<DciCascade> dci_cascade = new ArrayList<>();
public ArrayList<InternalVref> internal_vref = new ArrayList<>();

}

