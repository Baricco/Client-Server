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
                    <h3 className="Profile_Name">{this.name}</h3>
                    <img className="Profile_Image" src = {this.src} />
                </center>
            </div>
        );
    }
}