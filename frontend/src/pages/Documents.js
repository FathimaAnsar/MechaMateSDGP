import Header from "../components/Header";
import Button from 'react-bootstrap/Button';
function Documents() {
    return (
        <div >
            <Header/>
            <h2>Display documents page</h2>
            <Button variant="primary" className="button">Doc button</Button>{' '}
        </div>
    );
}

export default Documents;
