window.onload=function(){
    new Vue({
        el:'#loginDiv',
        data:{
            mailAddress: '',
            password: ''
        },
        methods:{
            register(){
                alert("The function is being developed.");
            },
            login(){
                axios.post('/login', {
                    mailAddress: this.mailAddress,
                    password: this.password
                }).then(function (response) {
                    if(response.data.returnCode == 0){
                        window.location.href = "/main"
                    } else if(response.data.returnCode == -1){
                        window.location.href = "/error"
                    } else {
                        alert("error MailAddress or Password");
                    }
                }).catch(function (error) {
                    if (error.response.status == 404) {
                        window.location.href = "/error404"
                    } else {
                        window.location.href = "/error"
                    }

                });
            },
            changeLang(event){
                var self = this;
                axios.post('/changeLanguage', {
                    lang: event.target.value
                }).then(function (response) {
                    if(response.data.returnCode == 0){
                        location.reload();
                    } else if(response.data.returnCode == -1){
                        console.log(response);
                    } else {
                        console.log(response);
                    }
                }).catch(function (error) {
                    console.log(error);
                });
            }
        }
    });
}