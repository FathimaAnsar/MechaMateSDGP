import Header from "../components/Header";
import Button from 'react-bootstrap/Button';


function Contact() {
    return (
        <div >
            <Header/>
            <h2>Contact Us</h2>
            <Button variant="danger" className="redbutton">Contact button</Button>{' '}

        </div>
    );
}

export default Contact;
