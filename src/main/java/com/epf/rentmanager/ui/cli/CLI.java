package com.epf.rentmanager.ui.cli;

public final class CLI {
    private CLI() {
    }

    /**
     *  Executes the CLI app.
     * @param args The command-line arguments
     */
    public static void main(final String[] args) {
        CLIMenu cliMenu = new CLIMenu();
        cliMenu.start();
    }
}