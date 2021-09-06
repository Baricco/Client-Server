import './styles/CardProfile.css';

export default class CardProfile {
    constructor(name, instaUsername){

        this.name = name;

        this.link = "https://www.instagram.com/" + instaUsername + "/";



        this.src = "img/profiles/" + name + ".jpg";
    }

    render() {
        return (
            <div className = "Card">
                <center>
                    <h3 className="family_title kerning_s cardText">{this.name}</h3>

                        <a href = {this.link} target="_blank">
                            <img className="InstaProfile" src = "img/instagram.png" />
                        </a>

                    <img className="Profile_Image" src = {this.src} />
                </center>
            </div>
        );
    }
}