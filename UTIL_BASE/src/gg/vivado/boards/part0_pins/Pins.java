/*

package:gg.vivado.boards.part0_pins

<pins>
  <complex>
    <pin array="pin" />
  </complex>
</pins>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.part0_pins.L_Part0Pins
    import:static gg.vivado.boards.part0_pins.U_Part0Pins
    class:Pins = extends Schema
    ArrayList<Pin>:pin = new ArrayList<>()

*/

package gg.vivado.boards.part0_pins;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.part0_pins.L_Part0Pins.*;
import static gg.vivado.boards.part0_pins.U_Part0Pins.*;

public class Pins extends Schema {

public ArrayList<Pin> pin = new ArrayList<>();

}

