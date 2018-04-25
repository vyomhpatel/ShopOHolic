package b12app.vyom.com.shopoholic.utility;

public class Events {


        public static class AdapterToHomeFragmentMessage {

            private String message;
            public AdapterToHomeFragmentMessage(String message) {
                this.message = message;
            }
            public String getMessage() {
                return message;
            }

        }

    public static class AdapterToCart {

        private int message;
        public AdapterToCart(int message) {
            this.message = message;
        }
        public int getMessage() {
            return message;
        }

    }




    public static class AdapterToActivityMessage {

        private int message;
        public AdapterToActivityMessage(int message) {
            this.message = message;
        }
        public int getMessage() {
            return message;
        }

    }

    public static class SubCategoryFragmentToProductListFragment {

        private String message;

        public SubCategoryFragmentToProductListFragment(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

    }


    public static class LoginToProfileUpdate {

        private String firstname, lastname, email, mobile;

        public LoginToProfileUpdate(String firstname, String lastname, String email, String mobile) {
            this.firstname = firstname;
            this.lastname = lastname;
            this.email = email;
            this.mobile = mobile;
        }


        public String getFirstname() {
            return firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public String getEmail() {
            return email;
        }



        public String getMobile() {
            return mobile;
        }
    }
}
