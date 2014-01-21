/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dr.util;

/**
 * Classe utilitária para realizar as conversões dos graus referente a direção
 * do vento
 *
 * @author andregnhoato
 */
public class WindUtil {

    static WindDirection direction;

    /**
     * Method that convert wind diretion degress in text 
     * @param wind wind direction in degrees
     * @return wind direction in text format
     */
    public static String getWindByDegress(Float wind) {

        String retorno = "N";
        
        if (wind <= 11.25) {
            retorno = WindDirection.N.toString();
        } else if (wind > 11.25 && wind <= 33.75) {
            retorno = WindDirection.NNE.toString();
        } else if (wind > 33.75 && wind <= 56.25) {
            retorno = WindDirection.NE.toString();
        } else if (wind > 56.25 && wind <= 78.75) {
            retorno = WindDirection.ENE.toString();
        } else if (wind > 78.75 && wind <= 101.25) {
            retorno = WindDirection.E.toString();
        } else if (wind > 101.25 && wind <= 123.75) {
            retorno = WindDirection.ESE.toString();
        } else if (wind > 123.75 && wind <= 146.25) {
            retorno = WindDirection.SE.toString();
        } else if (wind > 146.25 && wind <= 168.75) {
            retorno = WindDirection.SSE.toString();
        } else if (wind > 168.75 && wind <= 191.25) {
            retorno = WindDirection.S.toString();
        } else if (wind > 191.25 && wind <= 213.25) {
            retorno = WindDirection.SSW.toString();
        } else if (wind > 213.25 && wind <= 236.25) {
            retorno = WindDirection.SW.toString();
        } else if (wind > 236.25 && wind <= 258.75) {
            retorno = WindDirection.WSW.toString();
        } else if (wind > 258.75 && wind <= 282.25) {
            retorno = WindDirection.W.toString();
        } else if (wind > 282.25 && wind <= 303.75) {
            retorno = WindDirection.WNW.toString();
        } else if (wind > 303.75 && wind <= 326.75) {
            retorno = WindDirection.NW.toString();
        } else if (wind > 326.75 && wind <= 348.75) {
            retorno = WindDirection.NNW.toString();
        } else if (wind > 348.75 && wind <= 360) {
            retorno = WindDirection.N.toString();
        }
        return retorno;

    }

    /**
     * method responsable for convert enum in degrees float value
     *
     * @param wind
     * @return wind in degrees
     */
    public static Float getWindByText(WindDirection wind) {

        Float retorno = 0F;
        switch (wind) {
            case N:
                retorno = 0F;
                break;
            case NNE:
                retorno = new Float(22.5);
                break;
            case NE:
                retorno = new Float(45);
                break;
            case ENE:
                retorno = new Float(67.5);
                break;
            case E:
                retorno = new Float(90);
                break;
            case ESE:
                retorno = new Float(112.5);
                break;
            case SE:
                retorno = new Float(135);
                break;
            case SSE:
                retorno = new Float(157.5);
                break;
            case S:
                retorno = new Float(180);
                break;
            case SSW:
                retorno = new Float(202.5);
                break;
            case SW:
                retorno = new Float(225);
                break;
            case WSW:
                retorno = new Float(247.5);
                break;
            case W:
                retorno = new Float(270);
                break;
            case WNW:
                retorno = new Float(292.5);
                break;
            case NW:
                retorno = new Float(315);
                break;
            case NNW:
                retorno = new Float(337.5);
                break;
            default:
                retorno = 0F;

        }

        return retorno;
    }

    public enum WindDirection {

        N, NNE, NE, ENE, E, ESE, SE, SSE, S, SSW, SW, WSW, W, WNW, NW, NNW;
    }
}
