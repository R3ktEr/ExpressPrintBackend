package com.iesfranciscodelosrios.model.price;

public class Enums {
    public enum EndedType {
        no_ended(0),
        bound(1),
        stapled(2),
        twoHoles(3),
        fourHoles(4);

        private int icode;

        EndedType(int icode) {
            this.icode = icode;
        }

        public int getICode() {
            return this.icode;
        }
    }

    public enum ImpressionsTypes {
        normal(1),
        twoPages(2),
        twoSlides(3),
        fourSlides(4);

        private int icode;

        ImpressionsTypes(int icode) {
            this.icode = icode;
        }

        public int getICode() {
            return this.icode;
        }
    }

    public enum sheetSize {
        A3(0),
        A4(1),
        A5(2);

        private int icode;

        sheetSize(int icode) {
            this.icode = icode;
        }

        public int getICode() {
            return this.icode;
        }
    }

    public enum ThicknessType {
        G80(0),
        G160(1),
        G280(2);

        private int icode;

        ThicknessType(int icode) {
            this.icode = icode;
        }

        public int getICode() {
            return this.icode;
        }
    }

}
