import {BrowserRouter as Router} from 'react-router-dom'
import {ToastContainer} from 'react-toastify';
import PageRoutes from './routes/routes';
import {Container} from 'react-bootstrap';
import Header from './components/Header/Header';

const App = () => {
  return (
      <Router>
          <Container style={{maxWidth:'100vw', padding:0}}>
              <Header />
              <PageRoutes />
              <ToastContainer position="bottom-right"
                autoClose={5000}
                hideProgressBar={false}
                newestOnTop={false}
                closeOnClick
                rtl={false}
                pauseOnFocusLoss
                draggable
                pauseOnHover
                theme="dark"/>
          </Container>
      </Router>
    );
}

export default App;
