import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class App {
    private static List<Product> products;
    private static DefaultTableModel model;
    private static final String[] columnNames = {"餐廳名稱", "評分", "餐廳類型", "早餐", "午餐", "晚餐", "宵夜", "休息日", "標籤"};

    public static void main(String[] args) {
        // 建立框架
        JFrame frame = new JFrame("餐廳資訊");
        frame.setSize(1028, 700); // 設置框架大小為1028x700
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // 從檔案讀取資料
        products = readProductsFromFile("../../PROCESS_DATA/src/target.txt");

        // 創建表格模型並設置數據
        model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);
        for (Product product : products) {
            model.addRow(product.toTableRow());
        }

        JTable table = new JTable(model);
        table.setFont(new Font("黑體", Font.PLAIN, 15)); // 設置表格內容字體大小為15，且為普通體
        table.setRowHeight(85); // 設置每一行的高度為85
        table.getTableHeader().setFont(new Font("黑體", Font.PLAIN, 15)); // 設置表格標題字體大小為15，且為普通體
        table.getTableHeader().setBackground(new Color(145, 227, 221)); // 設置表格標題背景色

        // 設置表格樣式
        table.setShowGrid(true);
        table.setGridColor(new Color(200, 200, 200)); // 設置網格顏色
        table.setIntercellSpacing(new Dimension(1, 1)); // 設置網格間距

        // 只顯示橫線，不顯示直線
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);

        // 設置單元格渲染器支持多行顯示
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(new MultiLineCellRenderer());
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // 添加搜尋功能
        JPanel controlPanel = new JPanel(new FlowLayout());
        JTextField searchField = new JTextField(20);
        String[] dayOptions = {"所有星期", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};
        JComboBox<String> dayComboBox = new JComboBox<>(dayOptions); // 新增的星期選擇框
        String[] timeOptions = {"所有時段", "早", "午", "晚", "半夜"};
        JComboBox<String> timeComboBox = new JComboBox<>(timeOptions); // 新增的時段選擇框
        String[] ratingOptions = {"所有評分", "高於★", "高於★★", "高於★★★", "高於★★★★"};
        JComboBox<String> ratingComboBox = new JComboBox<>(ratingOptions); // 新增的評分選擇框
        JButton searchButton = new JButton("搜尋");
        JButton randomButton = new JButton("隨機選擇餐廳");

        controlPanel.add(new JLabel("搜尋: "));
        controlPanel.add(searchField);
        controlPanel.add(new JLabel("星期: "));
        controlPanel.add(dayComboBox); // 添加星期選擇框到面板
        controlPanel.add(new JLabel("時段: "));
        controlPanel.add(timeComboBox); // 添加時段選擇框到面板
        controlPanel.add(new JLabel("評分: "));
        controlPanel.add(ratingComboBox); // 添加評分選擇框到面板
        controlPanel.add(searchButton);
        controlPanel.add(randomButton); // 隨機按鈕移到搜尋按鈕後面

        // 隨機選擇按鈕動作
        randomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword = searchField.getText().toLowerCase();
                String day = dayComboBox.getSelectedItem().toString().toLowerCase();
                String time = timeComboBox.getSelectedItem().toString().toLowerCase();
                String rating = ratingComboBox.getSelectedItem().toString();
                List<Product> results = searchProducts(keyword, day, time, rating);
                Product randomProduct = getRandomProduct(results);
                if (randomProduct != null) {
                    showProductInfo(randomProduct);
                }
            }
        });

        // 搜尋按鈕動作
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword = searchField.getText().toLowerCase();
                String day = dayComboBox.getSelectedItem().toString().toLowerCase();
                String time = timeComboBox.getSelectedItem().toString().toLowerCase();
                String rating = ratingComboBox.getSelectedItem().toString();
                List<Product> results = searchProducts(keyword, day, time, rating);
                showSearchResults(results);
            }
        });

        // 添加控制面板和表格到框架
        frame.add(controlPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        // 顯示框架
        frame.setVisible(true);
    }

    private static Product getRandomProduct(List<Product> results) {
        if (results == null || results.isEmpty()) {
            JOptionPane.showMessageDialog(null, "沒有找到符合條件的餐廳", "隨機選擇的餐廳", JOptionPane.INFORMATION_MESSAGE);

            return null;
        }
        Random random = new Random();
        int index = random.nextInt(results.size());
        return results.get(index);
    }

    private static List<Product> searchProducts(String keyword, String day, String time, String rating) {
        List<Product> results = new ArrayList<>();
        for (Product product : products) {
            if (product.matches(keyword, day, time, rating)) {
                results.add(product);
            }
        }
        return results;
    }

    private static void showProductInfo(Product product) {
        String message = String.format("餐廳名稱: %s\n評分: %s\n餐廳類型: %s\n早餐: %s\n午餐: %s\n晚餐: %s\n宵夜: %s\n休息日: %s\n標籤: %s",
                product.name, product.score, product.priceAndType, product.breakfast, product.lunch, product.dinner, product.lateNight, product.rest, product.labels);
        JOptionPane.showMessageDialog(null, message, "隨機選擇的餐廳", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void showSearchResults(List<Product> results) {
        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(null, "沒有找到符合條件的餐廳", "搜尋結果", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder message = new StringBuilder("<html><body style='width: 600px;'>");
        for (Product product : results) {
            message.append("<div style='text-align: left; margin-bottom: 20px;'>")
                    .append("<span style='font-size: 12px;'>餐廳名稱: </span><span style='font-size: 12px;'>").append(product.name).append("</span><br>")
                    .append("<span style='font-size: 12px;'>評分: </span><span style='font-size: 12px;'>").append(product.score).append("</span><br>")
                    .append("<span style='font-size: 12px;'>餐廳類型: </span><span style='font-size: 12px;'>").append(product.priceAndType).append("</span><br>")
                    .append("<span style='font-size: 12px;'>早餐: </span><span style='font-size: 12px;'>").append(product.breakfast).append("</span><br>")
                    .append("<span style='font-size: 12px;'>午餐: </span><span style='font-size: 12px;'>").append(product.lunch).append("</span><br>")
                    .append("<span style='font-size: 12px;'>晚餐: </span><span style='font-size: 12px;'>").append(product.dinner).append("</span><br>")
                    .append("<span style='font-size: 12px;'>宵夜: </span><span style='font-size: 12px;'>").append(product.lateNight).append("</span><br>")
                    .append("<span style='font-size: 12px;'>休息日: </span><span style='font-size: 12px;'>").append(product.rest).append("</span><br>")
                    .append("<span style='font-size: 12px;'>標籤: </span><span style='font-size: 12px;'>").append(product.labels).append("</span><br>")
                    .append("<hr style='border-top: 1px dashed #8c8b8b;'>")
                    .append("</div>");
        }
        message.append("</body></html>");

        JLabel label = new JLabel(message.toString());
        label.setBackground(Color.WHITE); // 設置背景色為白色
        label.setOpaque(true); // 確保背景色生效

        JScrollPane scrollPane = new JScrollPane(label);
        scrollPane.setPreferredSize(new Dimension(600, 400));
        scrollPane.getViewport().setBackground(Color.WHITE); // 設置滾動視窗的背景色為白色
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // 提高滾動速度

        JOptionPane.showMessageDialog(null, scrollPane, "搜尋結果", JOptionPane.INFORMATION_MESSAGE);
    }

    private static List<Product> readProductsFromFile(String fileName) {
        List<Product> products = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("餐廳名稱: ")) {
                    String name = line.substring(5).trim();
                    double scoreValue = Double.parseDouble(reader.readLine().substring(4).trim());
                    String score = convertScoreToStars(scoreValue);
                    String PriceAndType = reader.readLine().substring(5).trim();

                    String breakfast = "";
                    String lunch = "";
                    String dinner = "";
                    String lateNight = "";
                    String rest = "";
                    String labels = "";

                    while ((line = reader.readLine()) != null && !line.startsWith("===============================================================================================================")) {
                        if (line.startsWith("早餐:")) {
                            breakfast = line.substring(3).trim();
                        } else if (line.startsWith("午餐:")) {
                            lunch = line.substring(3).trim();
                        } else if (line.startsWith("晚餐:")) {
                            dinner = line.substring(3).trim();
                        } else if (line.startsWith("宵夜:")) {
                            lateNight = line.substring(3).trim();
                        } else if (line.startsWith("休息日:")) {
                            rest = line.substring(4).trim();
                        } else if (line.startsWith("標籤:")) {
                            labels = line.substring(3).trim();
                        }
                    }

                    products.add(new Product(name, score, PriceAndType, breakfast, lunch, dinner, lateNight, rest, labels));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }

    private static String convertScoreToStars(double score) {
        if (score < 3) {
            return "★";
        } else if (score < 3.5) {
            return "★★";
        } else if (score < 4) {
            return "★★★";
        } else if (score < 4.5) {
            return "★★★★";
        } else {
            return "★★★★★";
        }
    }

    private static double convertStarsToScore(String stars) {
        switch (stars.length()) {
            case 1:
                return 1.0;
            case 2:
                return 3.0;
            case 3:
                return 3.5;
            case 4:
                return 4.0;
            case 5:
                return 4.5;
            default:
                return 0.0;
        }
    }

    static class Product {
        String name;
        String score;
        String priceAndType;
        String breakfast;
        String lunch;
        String dinner;
        String lateNight;
        String rest;
        String labels;

        public Product(String name, String score, String priceAndType, String breakfast, String lunch, String dinner, String lateNight, String rest, String labels) {
            this.name = name;
            this.score = score;
            this.priceAndType = priceAndType;
            this.breakfast = breakfast.isEmpty() ? "無" : breakfast;
            this.lunch = lunch.isEmpty() ? "無" : lunch;
            this.dinner = dinner.isEmpty() ? "無" : dinner;
            this.lateNight = lateNight.isEmpty() ? "無" : lateNight;
            this.rest = rest.isEmpty() ? "無" : rest;
            this.labels = labels.isEmpty() ? "無" : labels;
        }

        public boolean matches(String keyword, String day, String time, String rating) {
            boolean matchesKeyword = name.toLowerCase().contains(keyword) ||
                    labels.toLowerCase().contains(keyword);

            boolean matchesDay = false;
            switch (day) {
                case "所有星期":
                    matchesDay = true;
                    break;
                default:
                    matchesDay = !rest.contains(day);
            }

            boolean matchesTime = false;
            switch (time) {
                case "早":
                    matchesTime = !breakfast.equals("無");
                    break;
                case "午":
                    matchesTime = !lunch.equals("無");
                    break;
                case "晚":
                    matchesTime = !dinner.equals("無");
                    break;
                case "半夜":
                    matchesTime = !lateNight.equals("無");
                    break;
                case "所有時段":
                    matchesTime = true;
                    break;
                default:
                    matchesTime = true; // 如果沒有輸入時段，則匹配所有時段
            }

            boolean matchesRating = false;
            switch (rating) {
                case "所有評分":
                    matchesRating = true;
                    break;
                case "高於★":
                    matchesRating = convertStarsToScore(score) > 1;
                    break;
                case "高於★★":
                    matchesRating = convertStarsToScore(score) > 2;
                    break;
                case "高於★★★":
                    matchesRating = convertStarsToScore(score) > 3;
                    break;
                case "高於★★★★":
                    matchesRating = convertStarsToScore(score) > 4;
                    break;
            }

            return matchesKeyword && matchesDay && matchesTime && matchesRating;
        }

        public String[] toTableRow() {
            return new String[]{
                name,
                score,
                priceAndType,
                breakfast,
                lunch,
                dinner,
                lateNight,
                rest,
                labels
            };
        }

        @Override
        public String toString() {
            return "Product{" +
                    "name='" + name + '\'' +
                    ", score='" + score + '\'' +
                    ", priceAndType='" + priceAndType + '\'' +
                    ", breakfast='" + breakfast + '\'' +
                    ", lunch='" + lunch + '\'' +
                    ", dinner='" + dinner + '\'' +
                    ", lateNight='" + lateNight + '\'' +
                    ", rest='" + rest + '\'' +
                    ", labels='" + labels + '\'' +
                    '}';
        }
    }

    static class MultiLineCellRenderer extends JTextArea implements TableCellRenderer {
        public MultiLineCellRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
            setOpaque(true);
            setFont(new Font("黑體", Font.PLAIN, 15)); // 設置表格內容字體大小為15，且為普通體
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value != null ? value.toString() : "");
            setSize(table.getColumnModel().getColumn(column).getWidth(), getPreferredSize().height);
            return this;
        }
    }
}
