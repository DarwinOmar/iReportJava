package reporte;

import dao.DAOPostgres;
import dao.Parametro;
import java.awt.HeadlessException;
import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class Reporte extends DAOPostgres {

    /**
     * Este reporte es la primera version que realice, pero no contenia
     * parametros(a la bd)
     *
     * @param nombreArchivo nombre del archivo jasper
     * @throws java.lang.Exception
     */
    public void generarReporte(String nombreArchivo) throws Exception {
        String ubicacionReporte;
        try {
            ubicacionReporte = System.getProperty("user.dir") + "/src/reporte/" + nombreArchivo;
            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(ubicacionReporte);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, this.conexionReporte());
            JasperViewer view = new JasperViewer(jasperPrint, false);
            view.setVisible(true);
        } catch (JRException e) {
            throw e;
        }
    }

    /**
     * v2 Este metodo es la segunda version del metodo generar reporte
     *
     * @param nombreArchivo hace referencia a archivos jasper
     * @param listaParametro es la lista de parametro que se van a ingresar, el
     * pimer lavlor es el nombre del archivo y el siguente valor es el valor que
     * se le enviara
     * @param nombreArchivoGuardar
     * @param operacion
     * @throws java.lang.Exception
     */
    public void generarReporte(String nombreArchivo, List<Parametro> listaParametro) throws Exception {

        String ubicacionReporte;
        File fichero = null;
        Map param;
        param = new HashMap();
        try {
            ubicacionReporte = System.getProperty("user.dir") + "\\src\\reporte\\" + nombreArchivo;
            fichero = new File(ubicacionReporte);
            if (fichero.exists() == true) {
                JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(ubicacionReporte);
                if (listaParametro != null) {
                    for (Parametro par : listaParametro) {
                        param.put(par.getNombre(), par.getValor());
                    }
                } else {
                    param = null;
                }
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, param, this.conexionReporte());
                JasperViewer view = new JasperViewer(jasperPrint, false);
                view.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "NO EXISTE EL ARCHIVO");
            }
        } catch (JRException | HeadlessException e) {
            throw e;
        }
    }

    public void gReporte(String nombreArchivoJasper) throws ClassNotFoundException, SQLException, JRException {
        try {
            URL in = this.getClass().getResource(nombreArchivoJasper);             //se carga el reporte
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(in);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, this.conexionReporte());
            JasperViewer view = new JasperViewer(jasperPrint, false);
            view.setVisible(true);
        } catch (JRException ex) {
            throw ex;
        }
    }

    public void gReporte(String nombreArchivoJasper, List<Parametro> listaParametro) throws JRException, SQLException, ClassNotFoundException {
        Map param;
        param = new HashMap();
        try {

            URL in = this.getClass().getResource(nombreArchivoJasper);             //se carga el reporte
//            System.out.println(in); Me retorna la ruta del archivo
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(in);
            //se procesa el archivo jasper
            File archivo = new File(in.getPath());
            if (archivo.exists() == true) {
                if (listaParametro != null) {
                    for (Parametro par : listaParametro) {
                        param.put(par.getNombre(), par.getValor());
                    }
                } else {
                    param = null;
                }
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, param, this.conexionReporte());
                JasperViewer view = new JasperViewer(jasperPrint, false);
                view.setVisible(true);
                
            } else {
                JOptionPane.showMessageDialog(null, "No Existe el archivo");
            }
        } catch (JRException ex) {
            throw ex;
        }
    }

}
