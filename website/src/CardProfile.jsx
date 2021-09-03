import './styles/CardProfile.css';

export default class CardProfile {
    constructor(name){

        this.name = name;

        this.src = "img/profiles/" + name + ".jpg";
    }

    render() {
        return (
            <div className = "Card">
                <center>
                    <h3 className="family_title kerning_s font_m">{this.name}</h3>

                    <a href = "https://www.instagram.com/" >
                        <img className="InstaProfile" src = "img/instagram.png" />
                    </a>

                    <img className="Profile_Image" src = {this.src} />
                </center>
            </div>
        );
    }
}