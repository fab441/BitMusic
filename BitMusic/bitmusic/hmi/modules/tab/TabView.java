/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bitmusic.hmi.modules.tab;

import bitmusic.hmi.mainwindow.WindowComponent;
import bitmusic.hmi.patterns.AbstractView;
import bitmusic.hmi.patterns.ButtonColumn;
import bitmusic.hmi.patterns.Observable;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author unkedeuxke
 */
public final class TabView extends AbstractView<TabController> {

    private final String type = "TAB";

    private String title;
    private JLabel labelTitle;
    private JButton btnClose = new JButton("x");
    private JPanel panelTab;

    // Variables declaration - do not modify
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration

    public TabView() {
        super();
    }

    @Override
    public void initPanel() {
        System.out.println("--- TabView.initPanel()");

        // Initialisation du titre de l'onglet
        Integer nextTabId = WindowComponent.getInstance().getCentralAreaComponent().getView().getTabCounter();
        this.title = "Tab#" + nextTabId;
        this.labelTitle = new JLabel(title);

        // Attache du listener pour supprimer cet onglet
        this.btnClose.addActionListener(this.getController().new CloseTabListener());

        // Création et gestion de l'onglet en lui-même (petite partie du haut)
        this.panelTab = new JPanel(new GridBagLayout());
        panelTab.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        this.panelTab.add(this.labelTitle, gbc);
        gbc.gridx++;
        gbc.weightx = 0;
        panelTab.add(this.btnClose, gbc);

        // Contenu de l'onglet
        JPanel jPanel = this.getPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new JTable(this.getController().getModel().getModeleTable());

        jScrollPane1.setViewportView(jTable1);


        // Attache des listeners aux colonnes concernées
        ButtonColumn editerColumn = new ButtonColumn(this.jTable1, this.getController().getEditer(), 3);
        ButtonColumn infosColumn = new ButtonColumn(this.jTable1, this.getController().getInfos(), 4);
        ButtonColumn noterColumn = new ButtonColumn(this.jTable1, this.getController().getNoter(), 5);
        ButtonColumn sauvegarderColumn = new ButtonColumn(this.jTable1, this.getController().getSauvegarder(), 6);

        GroupLayout layout = new GroupLayout(this.getPanel());
        this.getPanel().setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1))
        );

    }

    public String getTitle() {
       return this.title;
    }

    public void setTitle(final String newTitle) {
       this.title = newTitle;
    }

    public JLabel getLabelTitle() {
        return this.labelTitle;
    }

    public void setLabelTitle(final JLabel newLabelTitle) {
        this.labelTitle = newLabelTitle;
    }

    public JButton getBtnClose() {
        return this.btnClose;
    }

    public void setBtnClose(final JButton newBtnClose) {
        this.btnClose = newBtnClose;
    }

    public JPanel getPanelTab() {
        return this.panelTab;
    }

    public void setPanelTab(final JPanel newPanelTab) {
        this.panelTab = newPanelTab;
    }

    @Override
    public String getType() {
       return type;
    }

    @Override
    public void update(Observable obj, String str) {
        System.out.println("----- TabView.update()");
    }
}
