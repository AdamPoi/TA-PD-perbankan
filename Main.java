import java.util.Scanner;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Main {
  static Scanner sc = new Scanner(System.in);
  static int customerCount = 3;
  static int historyCount = 0;
  static double[] account;
  static double[][] customerData = { { 1, 1, 200000, 500000, 0 },
      { 2, 2, 1000000, 700000, 0 },
      { 3, 1, 200000, 0, 1000000 },
      { 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0 } }; // [no rekening, jenis nasabah, simpanan, tabungan, pinjaman]
  static String[] historyMsg = new String[50];
  static int[] historyId = new int[50];

  public static void main(String[] args) {
    mainMenu();
  }

  // Main menu
  public static void mainMenu() {
    System.out.println("=========================================");
    System.out.println("\tSelamat Datang di Bank Aja");
    System.out.println("=========================================");
    System.out.println("\t\tMenu Utama");
    System.out.println("=========================================");
    System.out.println("Pilih Menu yang anda inginkan");
    System.out.println("1. Master Data");
    System.out.println("2. Transaksi");
    System.out.println("3. Tabungan");
    System.out.println("4. Pinjaman");
    System.out.println("5. History");
    System.out.println("6. Keluar");
    int pilihan = sc.nextInt();
    switch (pilihan) {
      case 1:
        dataMenu();
        break;
      case 2:
        transactionMenu();
        break;
      case 3:
        savingMenu();
        break;
      case 4:
        loanMenu();
        break;
      case 5:
        historyMenu();
        break;
      case 6:
        return;
      default:
        mainMenu();
        break;
    }
  }

  public static void dataMenu() {
    System.out.println("=========================================");
    System.out.println("\t\tMenu Data");
    System.out.println("=========================================");
    System.out.println("Pilih Menu yang anda inginkan");
    System.out.println("1. Lihat Seluruh Data Nasabah");
    System.out.println("2. Lihat Data Nasabah");
    System.out.println("3. Input Data Nasabah");
    System.out.println("4. Ubah Data Nasabah");
    System.out.println("5. Kembali");
    int pilihan = sc.nextInt();
    switch (pilihan) {
      case 1:
        showAllCustomerData();
        break;
      case 2:
        showCustomerData();
        break;
      case 3:
        inputCustomerData();
        break;
      case 4:
        editCustomerData();
        break;
      case 5:
        mainMenu();
        break;
      default:
        dataMenu();
        break;
    }
  }

  public static void showAllCustomerData() {
    if (customerData[0][0] == 0) {
      System.out.println("Maaf belum terdapat data nasabah !");
      dataMenu();
      return;
    }
    for (double[] row : customerData) {
      if (row[0] != 0) {
        System.out.println("=========================================");
        System.out.println("No Rekening : " + (int) row[0]);
        if (row[1] == 1) {
          System.out.println("Jenis Nasabah : Standar");
        } else {
          System.out.println("Jenis Nasabah : Prioritas");
        }
        System.out.println("Jumlah Saldo Simpanan : " + formatRupiah(row[2]));
        System.out.println("Jumlah Saldo Tabungan : " + formatRupiah(row[3]));
        System.out.println("Jumlah Saldo Pinjaman : " + formatRupiah(row[4]));
      }
    }
    addHistory(-1, "Menampilkan seluruh data nasabah");
    dataMenu();
  }

  public static void showCustomerData() {
    System.out.println("=========================================");
    account = getAccount();
    System.out.println("No Rekening : " + (int) account[0]);
    if (account[1] == 1) {
      System.out.println("Jenis Nasabah : Standar");
    } else {
      System.out.println("Jenis Nasabah : Prioritas");
    }
    System.out.println("Jumlah Saldo Simpanan : " + formatRupiah(account[2]));
    System.out.println("Jumlah Saldo Tabungan : " + formatRupiah(account[3]));
    System.out.println("Jumlah Saldo Pinjaman : " + formatRupiah(account[4]));
    addHistory(-1, "Menampilkan data nasabah dari rekening" + (int) account[0]);
    dataMenu();
  }

  public static void inputCustomerData() {
    System.out.println("=========================================");
    int customerType = inputAccountType();
    System.out.println("Masukkan jumlah deposit pertama : ");
    double depositAmount = sc.nextInt();
    customerData[customerCount][0] = customerCount + 1;
    customerData[customerCount][1] = customerType;
    customerData[customerCount][2] = depositAmount;
    customerCount += 1;
    addHistory(-1, "Menambahkan data nasabah rekening " + customerCount);
    dataMenu();
  }

  public static void editCustomerData() {
    System.out.println("=========================================");
    account = getAccount();
    int customerType = inputAccountType();
    account[1] = customerType;
    addHistory(-1, "Merubah data nasabah dari rekening" + (int) account[0]);
    dataMenu();
  }

  // Transaksi
  public static void transactionMenu() {
    System.out.println("=========================================");
    System.out.println("\t\tMenu Transaksi");
    System.out.println("=========================================");
    System.out.println("Pilih Menu yang anda inginkan");
    System.out.println("1. Cek Saldo");
    System.out.println("2. Setor Simpanan");
    System.out.println("3. Ambil Simpanan");
    System.out.println("4. Transfer");
    System.out.println("5. Kembali");
    int pilihan = sc.nextInt();
    switch (pilihan) {
      case 1:
        balanceCheck();
        break;
      case 2:
        depositBalance();
        break;
      case 3:
        takeBalance();
        break;
      case 4:
        transferBalance();
        break;
      case 5:
        mainMenu();
        break;
      default:
        transactionMenu();
        break;
    }
  }

  public static void balanceCheck() {
    System.out.println("=========================================");
    account = getAccount();
    System.out.printf("Jumlah saldo : %s\n", formatRupiah(account[2]));
    addHistory(account[0], "Check jumlah simpanan rekening " + (int) account[0]);
    transactionMenu();
  }

  public static void depositBalance() {
    System.out.println("=========================================");
    account = getAccount();
    System.out.print("Jumlah Deposit :");
    double depositAmount = sc.nextInt();
    account[2] += depositAmount;
    addHistory(account[0],
        String.format("Deposit simpanan ke rekening %d : %s", (int) account[0], formatRupiah(depositAmount)));
    transactionMenu();
  }

  public static void takeBalance() {
    System.out.println("=========================================");
    account = getAccount();
    System.out.print("Jumlah Penarikan :");
    double takeAmount = sc.nextInt();
    takeAmount += getAdminFee(account[1]);
    if (account[2] >= takeAmount) {
      account[2] -= takeAmount;
      addHistory(account[0],
          String.format("Penarikan simpanan dari rekening %d : %s", (int) account[0], formatRupiah(takeAmount)));
      transactionMenu();
      return;
    }
    System.out.println("Maaf, saldo anda tidak cukup!");
    transactionMenu();
  }

  public static void transferBalance() {
    System.out.println("Rekening Pengirim");
    double[] senderAccount = getAccount();
    System.out.println("Rekening Penerima");
    double[] receiverAccount = getAccount();
    System.out.println("=========================================");
    System.out.print("Jumlah transfer :");
    double transferAmount = sc.nextInt();
    double fee = getAdminFee(senderAccount[1]);
    if (senderAccount[2] >= (transferAmount + fee)) {
      senderAccount[2] -= transferAmount;
      senderAccount[2] -= fee;
      receiverAccount[2] += transferAmount;
      addHistory(account[0], String.format("Transfer dari rekening %d ke rekening %d : %s", (int) senderAccount[0],
          (int) receiverAccount[0], formatRupiah(transferAmount)));
      transactionMenu();
      return;
    }
    System.out.println("Maaf, saldo anda tidak cukup!");
    transactionMenu();
  }

  // Tabungan

  public static void savingMenu() {
    System.out.println("=========================================");
    System.out.println("\tMenu Tabungan ");
    System.out.println("=========================================");
    System.out.println("Pilih Menu yang anda inginkan");
    System.out.println("1. Data Tabungan");
    System.out.println("2. Setor Tabungan");
    System.out.println("3. Ambil Tabungan");
    System.out.println("4. Bunga Tabungan");
    System.out.println("5. Kembali");
    int pilihan = sc.nextInt();
    switch (pilihan) {
      case 1:
        showSavingData();
        break;
      case 2:
        depositSaving();
        break;
      case 3:
        takeSaving();
        break;
      case 4:
        account = getAccount();
        interestMenu(account, 3);
        savingMenu();
        break;
      case 5:
        mainMenu();
        break;
      default:
        savingMenu();
        break;
    }
  }

  public static void showSavingData() {
    System.out.println("=========================================");
    account = getAccount();
    System.out.printf("Jumlah Tabungan : %s\n", formatRupiah(account[3]));
    addHistory(account[0], "Check jumlah tabungan rekening " + (int) account[0]);
    savingMenu();
  }

  public static void depositSaving() {
    System.out.println("=========================================");
    account = getAccount();
    System.out.print("Jumlah Deposit Tabungan :");
    double depositAmount = sc.nextInt();
    if (account[2] >= depositAmount) {
      account[2] -= depositAmount;
      account[3] += depositAmount;
      addHistory(account[0],
          String.format("Deposit tabungan ke rekening %d : %s", (int) account[0], formatRupiah(depositAmount)));
      savingMenu();
      return;
    }
    System.out.println("Maaf, saldo anda tidak cukup!");
    savingMenu();
  }

  public static void takeSaving() {
    System.out.println("=========================================");
    account = getAccount();
    System.out.print("Jumlah Penarikan Tabungan :");
    double takeAmount = sc.nextInt();
    double fee = getAdminFee(account[1]);
    if (account[3] >= takeAmount && account[2] >= fee) {
      account[2] += takeAmount;
      account[2] -= fee;
      account[3] -= takeAmount;
      addHistory(account[0],
          String.format("Penarikan tabungan dari rekening %d : %s", (int) account[0], formatRupiah(takeAmount)));
      savingMenu();
      return;
    }
    System.out.println("Maaf, saldo anda tidak cukup!");
    savingMenu();
  }

  // Pinjaman
  public static void loanMenu() {
    System.out.println("=========================================");
    System.out.println("\tMenu Pinjaman ");
    System.out.println("=========================================");
    System.out.println("Pilih Menu yang anda inginkan");
    System.out.println("1. Data Pinjaman");
    System.out.println("2. Ambil Pinjaman");
    System.out.println("3. Bayar Pinjaman");
    System.out.println("4. Bunga Pinjaman");
    System.out.println("5. Kembali");
    int pilihan = sc.nextInt();
    switch (pilihan) {
      case 1:
        showLoanData();
        break;
      case 2:
        takeLoan();
        break;
      case 3:
        payLoan();
        break;
      case 4:
        account = getAccount();
        interestMenu(account, 4);
        loanMenu();
        break;
      case 5:
        mainMenu();
        break;
      default:
        loanMenu();
        break;
    }
  }

  public static void showLoanData() {
    System.out.println("=========================================");
    account = getAccount();
    System.out.printf("Jumlah Pinjaman : %s\n", formatRupiah(account[4]));
    addHistory(account[0], "Check jumlah pinjaman rekening " + (int) account[0]);
    loanMenu();
  }

  public static void takeLoan() {
    System.out.println("=========================================");
    account = getAccount();
    System.out.print("Jumlah Pengambilan Pinjaman : ");
    double takeAmount = sc.nextInt();
    double fee = getAdminFee(account[1]);
    if (account[2] >= fee) {
      account[4] += takeAmount;
      account[2] -= fee;
      addHistory(account[0],
          String.format("Mengambil pinjaman ke rekening %d : %s", (int) account[0], formatRupiah(takeAmount)));

      loanMenu();
      return;
    }
    System.out.println("Maaf, saldo anda tidak cukup!");
    loanMenu();
  }

  public static void payLoan() {
    System.out.println("=========================================");
    account = getAccount();
    System.out.println("Masukkan cara pembayaran pinjaman : ");
    System.out.println("1. Rekening Simpanan");
    System.out.println("2. Rekening Tabungan");
    System.out.println("3. Tunai");
    int pilihan = sc.nextInt();
    System.out.print("Jumlah Pembayaran Pinjaman :");
    double payAmount = sc.nextInt();
    int accountIndex = 0;
    switch (pilihan) {
      case 1:
        accountIndex = 2;
        break;
      case 2:
        accountIndex = 3;
        break;
    }
    if (accountIndex == 0) {
      account[4] -= payAmount;
    } else if (account[accountIndex] >= payAmount) {
      account[accountIndex] -= payAmount;
      account[4] -= payAmount;
    } else {
      System.out.println("Maaf, saldo anda tidak cukup!");
      loanMenu();
      return;
    }

    addHistory(account[0],
        String.format("Bayar pinjaman untuk rekening %d : %s", (int) account[0], formatRupiah(payAmount)));
    loanMenu();
  }

  // History

  public static void historyMenu() {
    System.out.println("=========================================");
    System.out.println("\t\tMenu History");
    System.out.println("=========================================");
    System.out.println("Pilih Menu yang anda inginkan");
    System.out.println("1. Lihat Seluruh History");
    System.out.println("2. Lihat History Nasabah");
    System.out.println("3. Kembali");
    int pilihan = sc.nextInt();
    switch (pilihan) {
      case 1:
        showAllHistory();
        break;
      case 2:
        showAccountHistory();
        break;
      case 3:
        mainMenu();
        break;
      default:
        historyMenu();
        break;
    }
  }

  public static void showAllHistory() {
    System.out.println("=========================================");
    System.out.println("\t\tHistory Transaksi");
    System.out.println("=========================================");
    for (int i = 0; i < historyMsg.length; i++) {
      if (historyMsg[i] != null) {
        System.out.printf("%d. %s\n", i + 1, historyMsg[i]);
      }
    }
    historyMenu();
  }

  public static void showAccountHistory() {
    account = getAccount();
    System.out.println("=========================================");
    System.out.println("\tHistory Transaksi Rekening " + account[0]);
    System.out.println("=========================================");
    int i = 1;
    for (int j = 0; j < historyId.length; j++) {
      if (historyId[j] == account[0]) {
        System.out.printf("%d. %s\n", i, historyMsg[j]);
        i++;
      }
    }
    historyMenu();
  }

  public static void addHistory(double id, String msg) {
    historyId[historyCount] = (int) id;
    historyMsg[historyCount] = msg;
    historyCount += 1;
  }

  // Bunga
  public static void interestMenu(double[] account, int accountIndex) {
    System.out.println("1. Hitung Bunga");
    System.out.println("2. Tambahkan Bunga");
    System.out.println("3. kembali");
    int pilihan = sc.nextInt();
    if (pilihan == 3) {
      return;
    }
    System.out.print("Masukkan jumlah tahun : ");
    int year = sc.nextInt();
    double interest = countInterest(account[1], account[accountIndex], year);
    switch (pilihan) {
      case 1:
        System.out.printf("Jumlah bunga setelah %d tahun : %s\n", year, formatRupiah(interest));
        addHistory(account[0],
            String.format("Menghitung bunga %d tahun pada rekening %d", year, (int) account[0]));
        break;
      case 2:
        account[accountIndex] = interest;
        System.out.printf("Bunga sukses ditambahkan ke rekening %d : %s\n", (int) account[0], formatRupiah(interest));
        addHistory(account[0],
            String.format("Menambahkan bunga %d tahun pada rekening %d : %s", year, (int) account[0],
                formatRupiah(interest)));
        break;
    }
  }

  public static double countInterest(double accountType, double nominal, int year) {
    double total = nominal;
    double interest;
    if (accountType == 1) {
      interest = 0.02;
    } else {
      interest = 0.04;
    }
    for (int i = 0; i < year; i++) {
      total += total * interest;
    }
    return total;
  }

  // Utils

  public static String formatRupiah(double nominal) {
    DecimalFormat rupiah = (DecimalFormat) DecimalFormat.getCurrencyInstance();
    DecimalFormatSymbols format = new DecimalFormatSymbols();
    format.setCurrencySymbol("Rp. ");
    format.setMonetaryDecimalSeparator(',');
    format.setGroupingSeparator('.');
    rupiah.setDecimalFormatSymbols(format);
    return rupiah.format(nominal);
  }

  public static double[] getAccount() {
    System.out.print("Masukkan no rekening : ");
    double account = sc.nextInt();
    for (double[] row : customerData) {
      if (row[0] == account) {
        System.out.println("=========================================");
        return row;
      }
    }
    System.out.println("No rekening tidak ditemukan !");
    System.out.println("=========================================");
    return getAccount();
  }

  public static int inputAccountType() {
    System.out.println("Masukkan jenis rekening untuk nasabah : ");
    System.out.println("1. Standar");
    System.out.println("2. Prioritas");
    int customerType = sc.nextInt();
    if (!(customerType == 1 || customerType == 2)) {
      System.out.println("Input yang anda masukan tidak valid !");
      customerType = inputAccountType();
    }
    return customerType;
  }

  public static int getAdminFee(double accountType) {
    if (accountType == 1) {
      return 5000;
    } else {
      return 2000;
    }
  }
}
