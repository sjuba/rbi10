/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reporter;

import reporter.xml.SEnumDataType;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import reporter.xml.SEnumFilterComparison;

/**
 *
 * @author Alfredo Pérez
 */
public abstract class SReporterUtils {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    /*
     * Private methods:
     */
    /**
     * Normalizes supported data types to Double or String objects.
     *
     * @param value Value to normalize.
     * @return Normalized value: a Double or a String.
     * @throws Exception
     */
    private static Object normalizeValue(final Object value) throws Exception {
        Object normalizedValue = null;

        if (value instanceof Boolean) {
            normalizedValue = ((boolean) value) ? 1d : 0d;
        } else if (value instanceof Number) {
            normalizedValue = (double) value;
        } else if (value instanceof String) {
            normalizedValue = value;
        } else if (value instanceof Date) {
            normalizedValue = (double) ((Date) value).getTime();
        }

        return normalizedValue;
    }

    /*
     * Public methods:
     */
    /**
     * Checks if value is an instance of expected class.
     *
     * @param dataType Data type.
     * @param value Value to check.
     * @throws Exception
     */
    public static void checkDataType(final SEnumDataType dataType, final Object value) throws Exception {
        switch (dataType) {
            case BOOLEAN:
                if (!(value instanceof Boolean)) {
                    throw new ClassCastException(Boolean.class.getName());
                }
                break;
            case LONG:
                if (!(value instanceof Long)) {
                    throw new ClassCastException(Long.class.getName());
                }
                break;
            case DOUBLE:
                if (!(value instanceof Double)) {
                    throw new ClassCastException(Double.class.getName());
                }
                break;
            case STRING:
                if (!(value instanceof String)) {
                    throw new ClassCastException(String.class.getName());
                }
                break;
            case DATE:
            case DATETIME:
            case TIME:
                if (!(value instanceof Date)) {
                    throw new ClassCastException(Date.class.getName());
                }
                break;
            default:
        }
    }

    /**
     * Casts value to desired data type.
     *
     * @param dataType Data type.
     * @param value Value to cast.
     * @return Casted value.
     * @throws Exception
     */
    public static Object castDataType(final SEnumDataType dataType, final String value) throws Exception {
        Object cast = null;

        switch (dataType) {
            case BOOLEAN:
                cast = value.compareTo("1") == 0 || Boolean.parseBoolean(value);
                break;
            case LONG:
                cast = Long.parseLong(value);
                break;
            case DOUBLE:
                cast = Double.parseDouble(value);
                break;
            case STRING:
                cast = value;
                break;
            case DATE:
                cast = DATE_FORMAT.parse(value);
                break;
            case DATETIME:
                cast = DATE_TIME_FORMAT.parse(value);
                break;
            case TIME:
                cast = TIME_FORMAT.parse(value);
                break;
            default:
        }

        return cast;
    }

    /**
     * Compares values 1 and 2 with desired Boolean comparison.
     *
     * @param value1 Value 1.
     * @param value2 Value 2.
     * @param comparison Boolean comparisson.
     * @return Result of Boolean comparisson.
     * @throws Exception
     */
    public static boolean compareValues(final Object value1, final Object value2, final SEnumFilterComparison comparison) throws Exception {
        boolean result = false;
        Object normalizedValue1 = normalizeValue(value1) == null ? "" : normalizeValue(value1);
        Object normalizedValue2 = normalizeValue(value2) == null ? "" : normalizeValue(value2);

        if (normalizedValue1 instanceof Double && normalizedValue2 instanceof Double) {
            double d1 = (double) normalizedValue1;
            double d2 = (double) normalizedValue2;

            switch (comparison) {
                case LESS:
                    result = d1 < d2;
                    break;
                case LESS_EQUAL:
                    result = d1 <= d2;
                    break;
                case GREATER:
                    result = d1 > d2;
                    break;
                case GREATER_EQUAL:
                    result = d1 >= d2;
                    break;
                case EQUAL:
                    result = d1 == d2;
                    break;
                case DIFFERENT:
                    result = d1 != d2;
                    break;
                default:
            }
        } else if (normalizedValue1 instanceof String && normalizedValue2 instanceof String) {
            String s1 = (String) normalizedValue1;
            String s2 = (String) normalizedValue2;

            switch (comparison) {
                case LESS:
                    result = s1.compareTo(s2) < 0;
                    break;
                case LESS_EQUAL:
                    result = s1.compareTo(s2) <= 0;
                    break;
                case GREATER:
                    result = s1.compareTo(s2) > 0;
                    break;
                case GREATER_EQUAL:
                    result = s1.compareTo(s2) >= 0;
                    break;
                case EQUAL:
                    result = s1.compareTo(s2) == 0;
                    break;
                case DIFFERENT:
                    result = s1.compareTo(s2) != 0;
                    break;
                default:
            }
        } else {
            throw new Exception("Unsupported or incompatible values!");
        }

        return result;
    }

    /**
     *
     * @param paramsFinal
     * @param paramsName
     * @param paramName
     * @return
     */
    public static Object findParamInParams(final HashMap<String, SReporterParam> paramsFinal, final String paramsName, final String paramName) {
        SReporterParam Aux = null;
        Aux = paramsFinal.get(paramsName);
        try {
            Aux.getValue();
        } catch (Exception e) {
            System.out.println("No se encontro el valor :\"" + paramName + "\" en \"" + paramsName + "\".");
            return "";
        }
        return Aux.getValue();
    }

    public static int findPositionColumnInDataSource(final HashMap<String, ArrayList> hasColumnsNames, final String dataSource, final Object value) {
        ArrayList Aux = null;
        Aux = hasColumnsNames.getOrDefault(dataSource, null);
        for (int i = 0; i < Aux.size(); i++) {
            if (Aux.get(i).toString().toLowerCase().equals(value.toString().toLowerCase())) {
                return i;
            }
        }
        return -1;
    }

    /**
     *
     * @param hArrayArrayResult This had all DataSources (DB in memory).
     * @param Datasource (String) The data source where it will be searched.
     * @param value (Object) Value to find in the @param DataSource indicated.
     * @param columnNumber (int) Number of the column where it will be searched.
     */
    public static void findDatasourceInDatasources(final HashMap<String, ArrayList<ArrayList<Object>>> hArrayArrayResult, final String Datasource, final Object value, final int columnNumber) {
        System.out.println("");

        for (int i = 0; i < hArrayArrayResult.get(Datasource).size(); i++) {
            if (hArrayArrayResult.get(Datasource).get(i).get(columnNumber).equals(value.toString())) {
                System.out.println("Encontrado");
            }
        }
    }

    /**
     *
     * @param hArrayArrayResult (HashMap) with all DataSources (DB in memory).
     * @param Datasource (String) The datasource where it will be searched.
     * @param columnNumber Column Number to Return.
     * @return (ArrayList(object)) outColumn.
     *
     */
    public static ArrayList<Object> getDataSourceFromDatasSources(final HashMap<String, ArrayList<ArrayList<Object>>> hArrayArrayResult, final String Datasource, final int columnNumber) {
        ArrayList<Object> outColumn = new ArrayList<>();
        for (int i = 0; i < hArrayArrayResult.get(Datasource).size(); i++) {
            outColumn.add(hArrayArrayResult.get(Datasource).get(i).get(columnNumber));
        }
        return outColumn;

    }

    /**
     *
     * @param concatenation
     * @param hArrayArrayResult (HashMap) with all DataSources (DB in memory).
     * @param Datasource (String) The datasource where it will be searched.
     * @param columnNumber
     */
    public static void prossesOperandData(final String concatenation, final HashMap<String, ArrayList<ArrayList<Object>>> hArrayArrayResult, final String Datasource, final int columnNumber) {
        String delimitadores = " ";
        String[] palabras = concatenation.split(delimitadores);
        ArrayList elementosCreados = new ArrayList();
        ArrayList<String> valores = new ArrayList<>();
        ArrayList<String> comparisson = new ArrayList<>();

        elementosCreados = getDataSourceFromDatasSources(hArrayArrayResult, Datasource, columnNumber);

        //La ultima palabra es el operator
        for (int i = 0; i < palabras.length - 1; i++) {
            if (i % 2 == 0) {
                valores.add(palabras[i]);
            } else {
                comparisson.add(palabras[i]);
            }
        }

        System.out.println("Operator: " + palabras[palabras.length - 1]);
        for (int i = 0; i < valores.size(); i++) {
            for (int j = 0; j < elementosCreados.size(); j++) {
                switch (comparisson.get(i)) {
                    case "=":
                        if (Double.parseDouble((String) (elementosCreados.get(j).toString())) == Double.parseDouble(valores.get(i))) {
                            System.out.println("cumple la condicion! " + Double.parseDouble((String) (elementosCreados.get(j).toString())));
                        }
                        break;
                    case "<":
                        if (Double.parseDouble((String) (elementosCreados.get(j).toString())) < Double.parseDouble(valores.get(i))) {
                            System.out.println("cumple la condicion! " + Double.parseDouble((String) (elementosCreados.get(j).toString())));
                        }
                        break;
                    case ">":
                        if (Double.parseDouble((String) (elementosCreados.get(j).toString())) > Double.parseDouble(valores.get(i))) {
                            System.out.println("cumple la condicion! " + Double.parseDouble((String) (elementosCreados.get(j).toString())));
                        }
                        break;
                    case "<=":
                        if (Double.parseDouble((String) (elementosCreados.get(j).toString())) <= Double.parseDouble(valores.get(i))) {
                            System.out.println("cumple la condicion! " + Double.parseDouble((String) (elementosCreados.get(j).toString())));
                        }
                        break;
                    case ">=":
                        if (Double.parseDouble((String) (elementosCreados.get(j).toString())) >= Double.parseDouble(valores.get(i))) {
                            System.out.println("cumple la condicion! " + Double.parseDouble((String) (elementosCreados.get(j).toString())));
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
